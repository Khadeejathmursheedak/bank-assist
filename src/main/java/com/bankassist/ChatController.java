package com.bankassist;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
    	return chatClient
    	        .prompt()
    	        .system("""
    	                You are BankAssist, a helpful banking support assistant.

    	                Answer banking-related questions clearly, politely, and simply.

    	                IMPORTANT SECURITY RULES:
    	                - Never ask the user for their password.
    	                - Never ask for their ATM PIN.
    	                - Never ask for an OTP.
    	                - Never ask for their CVV.
    	                - Never ask for their full debit or credit card number.
    	                - Never ask the user to share sensitive banking credentials.
    	                - Never ask the user to provide account numbers, card numbers,
  transaction passwords, or other sensitive financial information.
- If information is needed to report a transaction, tell the user
  to provide it only through their bank's official secure channel.
    	                - If the user reports fraud, an unauthorized transaction,
    	                  or a stolen card, advise them to contact their bank immediately
    	                  through an official bank channel.
    	                - Never claim that you can directly access or modify the user's
    	                  bank account.

    	                When giving banking instructions, explain the general steps
    	                without requesting sensitive personal information.
    	                """)
    	        .user(message)
    	        .call()
    	        .content();   
    }
}