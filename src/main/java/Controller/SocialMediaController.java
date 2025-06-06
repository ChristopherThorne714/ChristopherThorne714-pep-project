package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController() {
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * DONE
     * TODO: create the following routes: 
     * get("/messages/{message_id}"); //
     * delete("/messages/{message_id}"); //
     * get("/accounts/{account_id}/messages/");
     * patch("/messages/{message_id}");
     * post("/login");
     * post("/register");
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::createAccountHandler);
        app.post("/login", this::loginHandler);

        app.get("/example-endpoint", this::exampleHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesHandler);
        app.post("/messages", this::createMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * should attempt to create an account with the given information form ctx and resonds with a json of the new account if successful or status 400 if not
     * @param ctx
     */
    private void createAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.addAccount(account);
        if(createdAccount != null) {
            ctx.json(createdAccount);
        } else {
            ctx.status(400);
        }
    }

    /**
     * should attempt to validate login credentials against the db
     * @param ctx
     */
    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account validatedAccount = accountService.login(account);
        if(validatedAccount != null) {
            ctx.json(validatedAccount);
        } else {
            ctx.status(401);
        }
    }

    /**
     * @response should contain the list of retrieved messages
     * @param ctx
     */
    private void getMessagesHandler(Context ctx) {
        List<Message> messageList = messageService.getAllMessages();
        ctx.json(messageList);
    }


    /**
     * @status 200 if message found, 400 otherwise
     * @response should be the found message object
     * @param ctx
     */
    private void getMessageByIdHandler(Context ctx) {
        Message message = messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if (message == null) { 
            return;
        } else {
            ctx.json(message);
        }
    }

    /* */
    private void getAccountMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAccountMessages(Integer.parseInt(ctx.pathParam("account_id")));
        ctx.json(messages);
    }

    /**
     * @response should contain the newly created message
     * @status should be 200 if successful, 400 if unsuccessful
     * @param ctx
     * @throws JsonProcessingException
     */
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);

        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * @response should return an updated message
     * @status should be 200 if successful, 400 if unsuccessful
     * @param ctx
     */
    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(Integer.parseInt(ctx.pathParam("message_id")), message);
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() == true || message.getMessage_text().isEmpty() == true) {
            ctx.status(400);
        } else if (updatedMessage != null) {
            ctx.json(mapper.writeValueAsString(updatedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * @response should contain the deleted message entry
     * @status should be 200 if successful, 400 if unsuccessful
     * @param ctx
     */
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message deletedMessage = messageService.removeMessage(Integer.parseInt(ctx.pathParam("message_id")));
        if (deletedMessage == null) {
            ctx.json("");
        } else {
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }
    }


}