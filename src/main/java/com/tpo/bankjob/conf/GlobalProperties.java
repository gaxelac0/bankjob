package com.tpo.bankjob.conf;

//@Configuration
//@PropertySource("classpath:global.properties")
public class GlobalProperties {

   // @Value("${dias}")
    private int dias;

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}
}