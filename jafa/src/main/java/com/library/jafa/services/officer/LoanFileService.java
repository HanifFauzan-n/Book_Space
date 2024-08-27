package com.library.jafa.services.officer;

import java.io.IOException;

import org.springframework.stereotype.Service;


@Service
public interface LoanFileService {
    byte[] generateReport() throws IOException;
}
