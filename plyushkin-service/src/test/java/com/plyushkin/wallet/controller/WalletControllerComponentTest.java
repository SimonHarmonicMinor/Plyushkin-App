package com.plyushkin.wallet.controller;

import com.plyushkin.openapi.client.WalletCreateRequest;
import com.plyushkin.openapi.client.WalletUpdateRequest;
import com.plyushkin.testutil.db.TestDbFacade;
import com.plyushkin.testutil.rest.TestControllers;
import com.plyushkin.testutil.slices.ComponentTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.plyushkin.testutil.CustomMatchers.containsBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    @SneakyThrows
    void shouldCreateMultipleWallets() {
        rest.walletController().createWallet(new WalletCreateRequest().name("my wallet 1"));
        rest.walletController().createWallet(new WalletCreateRequest().name("my wallet 2"));

        final var wallets = rest.walletController().getWallets();

        assertThat(wallets, allOf(
                hasSize(2),
                containsBy(w -> w.getName().equals("my wallet 1")),
                containsBy(w -> w.getName().equals("my wallet 2"))
        ));
    }

    @Test
    @SneakyThrows
    void shouldUpdateWallet() {
        final var walletId =
                rest.walletController()
                        .createWallet(new WalletCreateRequest().name("my wallet 1"))
                        .getId();

        rest.walletController().updateWallet(
                walletId,
                new WalletUpdateRequest().name("new wallet")
        );

        final var walletById = rest.walletController()
                .getWalletById(walletId);

        assertEquals("new wallet", walletById.getName());
    }
}
