package com.application.enums;

import java.util.Calendar;

public enum SemanaEnum {
	SEGUNDA(Calendar.MONDAY, "Segunda"), TERCA(Calendar.TUESDAY, "Terça"), QUARTA(Calendar.WEDNESDAY, "Quarta"),
	QUINTA(Calendar.THURSDAY, "Quinta"), SEXTA(Calendar.FRIDAY, "Sexta"), SABADO(Calendar.SATURDAY, "Sabado"),
	DOMINGO(Calendar.SUNDAY, "Domingo");

	private Integer codigo;
	private String descricao;

	private SemanaEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static SemanaEnum getSemanaEnum(String descricao) {
		for (SemanaEnum semanaEnum : SemanaEnum.values()) {
			if (semanaEnum.getDescricao().equalsIgnoreCase(descricao)) {
				return semanaEnum;
			}
		}
		return null;
	}

	public static SemanaEnum getSemanaEnum(Integer codigo) {
		for (SemanaEnum semanaEnum : SemanaEnum.values()) {
			if (semanaEnum.getCodigo().equals(codigo)) {
				return semanaEnum;
			}
		}
		return null;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}
