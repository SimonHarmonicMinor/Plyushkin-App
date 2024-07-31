package com.plyushkin.wallet.controller;

import com.plyushkin.testutil.db.TestDbFacade;
import com.plyushkin.testutil.rest.TestControllers;
import com.plyushkin.testutil.slices.ComponentTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.plyushkin.openapi.client.WalletCreateRequest;

import static org.junit.jupiter.api.Assertions.*;

@ComponentTest
class WalletControllerComponentTest {
    @Autowired
    private TestDbFacade db;
    @Autowired
    private TestControllers rest;

    @BeforeEach
    void beforeEach() {
        db.cleanDatabase();
    }

    @Test
    @SneakyThrows
    void shouldCreateWallet() {
        final var myWallet = rest.walletController().createWallet(new WalletCreateRequest().name("my wallet"));

        assertNotNull(myWallet.getCreatedBy());
        assertNotNull(myWallet.getId());
        assertEquals("my wallet", myWallet.getName());
    }
}