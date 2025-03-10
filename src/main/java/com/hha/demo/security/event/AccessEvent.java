package com.hha.demo.security.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessEvent {

	private String userName;
	private String password;
	private LocalDateTime access_at;
	
	@Override
	public String toString() {
		StringBuilder eventString = new StringBuilder();
		eventString.append(userName).append(" tried to access with ");
		eventString.append(password).append(" at ").append(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(access_at));
		return eventString.toString();
	}
}
