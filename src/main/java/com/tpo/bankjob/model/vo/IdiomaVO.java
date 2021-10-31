package com.tpo.bankjob.model.vo;

public class IdiomaVO {
	
	private String idioma;
	private IdiomaLevelEnum level;
	
	public IdiomaVO(String idioma, IdiomaLevelEnum level) {
		this.idioma = idioma;
		this.level = level;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public IdiomaLevelEnum getLevel() {
		return level;
	}

	public void setLevel(IdiomaLevelEnum level) {
		this.level = level;
	}

	
}
