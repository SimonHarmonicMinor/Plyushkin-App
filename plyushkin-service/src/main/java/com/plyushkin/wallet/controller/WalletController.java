package com.plyushkin.wallet.controller;

import com.plyushkin.infra.DefaultErrorResponse;
import com.plyushkin.user.CurrentUserIdProvider;
import com.plyushkin.infra.WriteTransactional;
import com.plyushkin.wallet.domain.Wallet;
import com.plyushkin.wallet.WalletId;
import com.plyushkin.wallet.controller.request.WalletCreateRequest;
import com.plyushkin.wallet.controller.request.WalletUpdateRequest;
import com.plyushkin.wallet.controller.response.WalletResponse;
import com.plyushkin.wallet.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
class WalletController {
    private final WalletRepository walletRepository;
    private final CurrentUserIdProvider currentUserIdProvider;

    @PostMapping("/wallets")
    @WriteTransactional
    @SneakyThrows
    public ResponseEntity<WalletResponse> createWallet(
            @RequestBody
            @NotNull
            @Valid
            WalletCreateRequest request
    ) {
        final var wallet = walletRepository.save(
                Wallet.create(
                        WalletId.create(System.currentTimeMillis()),
                        request.name(),
                        currentUserIdProvider.get()
                )
        );
        return ResponseEntity.created(
                        URI.create("/api/wallets/%s".formatted(wallet.getId()))
                )
                .body(new WalletResponse(wallet));
    }

    @GetMapping("/wallets/{walletId}")
    @Transactional(readOnly = true)
    @PreAuthorize("@WalletAuth.hasAccessForWalletView(#walletId)")
    public WalletResponse getWalletById(
            @PathVariable @NotNull WalletId walletId
    ) {
        return walletRepository.findById(walletId)
                .map(WalletResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Wallet by id=" + walletId));
    }

    @GetMapping("/wallets")
    @Transactional(readOnly = true)
    public List<WalletResponse> getWallets() {
        final var currentUser = currentUserIdProvider.get();
        return walletRepository.findAllByCreatedBy(currentUser)
                .stream()
                .map(WalletResponse::new)
                .toList();
    }

    @PatchMapping("/wallets/{walletId}")
    @WriteTransactional
    @PreAuthorize("@WalletAuth.hasAccessForWalletUpdate(#walletId)")
    public WalletResponse updateWallet(
            @PathVariable @NotNull WalletId walletId,
            @RequestBody @NotNull @Valid WalletUpdateRequest request
    ) {
        final var wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Wallet by id=" + walletId));
        wallet.update(request.name());
        return new WalletResponse(walletRepository.save(wallet));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("Handled EntityNotFoundException", e);
        return ResponseEntity.status(404)
                .body(new DefaultErrorResponse(e));
    }
}
