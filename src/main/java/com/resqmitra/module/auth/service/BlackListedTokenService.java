package com.resqmitra.module.auth.service;

public interface BlackListedTokenService {

	boolean isTokenBlackListed(String jwt);
	
	
}
