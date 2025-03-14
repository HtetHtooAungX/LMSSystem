package com.hha.demo.controller;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hha.demo.controller.generator.ErrorMessageGenerator;
import com.hha.demo.dto.input.LoginDto;
import com.hha.demo.dto.input.SignUpDto;
import com.hha.demo.entity.User;
import com.hha.demo.entity.User.Role;
import com.hha.demo.entity.validation.UserValidator;
import com.hha.demo.security.event.AccessEvent;
import com.hha.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private static final String privkey = "MIICXQIBAAKBgQCCEb5E5SfVHqsm8YUxAKDSeLPgfK7BVvArAN56g90CG9ka01O8w/NlB1/abfBDts6FJ0r6Xpk0Sa1WC9mMrOYevmY3s5eMKfSc2chJrDI2aT0kdKuU0dv5oNU7zp6COg5TmaTZOaG2KFML+1ihguSWpPb4PU/b5jkCLyK02RGqzQIDAQABAoGAebtRb/loRjXlyRTRqwMDgPgmoTsP8zMCo7y0e8Vd2tNlZY3TGR/rc+pq82thKn602jnkGl7e0+kAqT995AMNevKEjwGtu6+yuzQWZYZjfQLJmBmJD+efky2UuyK5IbmTL3Jka479oJBZGv7U2T3d3pXZsxoHdc5HBZHykVdSrKECQQDADcDRAYKCh2h4yg5XbzSZZ0YnkXqI+blD+kNKblWyQTB04kzcuYYpVNwBSWjh/B9/ovlAFYVegrVXrED2ZfwTAkEArWCSX0+QDxNGDArpxeptIlBuMskxa8dCvsnFoeu0yH2IF1q/b2ZnKiAqESp3jp04ML8KOdpo3t90K7z1VHrZnwJBAJW/45HUyIu4w4gznN0mM/BNa5FcyRvXBFNx51g5Eg8M75ij/+S3sFm39lf2gpZ6/aCLVihW34hjCL1U7c5ylQsCQDPMcm6/Wo7mdzsJ16Ylz808sm/B5F4K/kn6Bm3F+hEcUBiKKD+kHJZdXbQbN/UHy83kha2bL1HG+PJIVhOTUIkCQQCOCA1CBIY7GnjDVPypW4+X0DtSwy/qGJJyszZfW8z2uG0KC37fIimPuXsrJbn86IrWnvXL8+A5wi9rXV8uNl8J";

	private final ApplicationEventPublisher eventPublisher;
	private final UserService service;
	private final UserValidator userValidator;

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@PostMapping("/check-login")
	public String checkLogin(@ModelAttribute LoginDto dto) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		
		String decryptedPassword = passwordDecryption(dto);

		eventPublisher.publishEvent(new AccessEvent(dto.getUsername(), decryptedPassword, LocalDateTime.now()));

		User user = service.findByUserNameOrEmail(dto.getUsername());
		if (null != user && StringUtils.hasText(decryptedPassword)) {
			
			List<Role> role = new ArrayList();
			role.add(user.getRole());
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					user.getUsername(), decryptedPassword, role.stream()
							.map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return "redirect:/";
		}

		return "login";
	}

	@GetMapping("/after-logout")
	public String logout() {
		return "logout";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute @Valid User user,
			BindingResult result, RedirectAttributes reAttributes) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userValidator.validate(user, result, authentication);
		
		if (result.hasErrors()) {
			ErrorMessageGenerator.generateUserErrorMessage(result, reAttributes);
			return "redirect:/auth/signup";
		}
		
		service.register(user);
		return "redirect:/";
	}

	private String passwordDecryption(LoginDto dto) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, generatePrivateKey(privkey));

		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(dto.getPassword()));
		String decryptedPassword = new String(decrypted, StandardCharsets.UTF_8);
		return decryptedPassword;
	}

	public PrivateKey generatePrivateKey(String keyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] data = Base64.getDecoder().decode(keyString);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(data);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey key = factory.generatePrivate(spec);
		return key;
	}
}
