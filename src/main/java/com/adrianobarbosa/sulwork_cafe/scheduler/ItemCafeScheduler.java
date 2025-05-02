package com.adrianobarbosa.sulwork_cafe.scheduler;

import com.adrianobarbosa.sulwork_cafe.service.ItemCafeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ItemCafeScheduler {

    private final ItemCafeService itemCafeService;

    public ItemCafeScheduler(ItemCafeService itemCafeService) {
        this.itemCafeService = itemCafeService;
    }

    // Executa todos os dias à meia-noite
    @Scheduled(cron = "0 0 0 * * ?")
    public void atualizarStatusItens() {
        itemCafeService.atualizarStatusItensVencidos();
    }
}
