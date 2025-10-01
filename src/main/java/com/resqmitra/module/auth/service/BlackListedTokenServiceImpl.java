package com.resqmitra.module.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resqmitra.module.auth.entity.BlackListedToken;
import com.resqmitra.module.auth.repo.BlackListedTokenRepo;

@Service
public class BlackListedTokenServiceImpl implements BlackListedTokenService{

	@Autowired
	private BlackListedTokenRepo tokenRepo;
	
	@Override
	public boolean isTokenBlackListed(String jwt) {
		Optional<BlackListedToken> token = tokenRepo.findById(jwt);
		return token.isPresent();
	}

}
