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
    // AccountService accountService;

    /**
     * TODO: create the following routes: 
     * get("/messages/{message_id}");
     * delete("/messages/{message_id}");
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
        app.get("/example-endpoint", this::exampleHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.post("/messages", this::createMessageHandler);
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
     * response should contain the list of retrieved messages
     * @param ctx
     */
    private void getMessagesHandler(Context ctx) {
        List<Message> messageList = messageService.getAllMessages();
        ctx.json(messageList);
    }


    /**
     * response should be 
     * @param ctx
     */
    private void getMessageByIdHandler(Context ctx) {
        ctx.json(messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id"))));
    }

    /**
     * response should contain the newly created message
     * @param ctx
     * @throws JsonProcessingException
     */
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        if ((messageService.addMessage(message)) != null) {
            ctx.json(mapper.writeValueAsString(message));
        } else {
            ctx.status(400);
        }
    }


    /**
     * response should contain the deleted message entry
     * @param ctx
     */
    private void deleteMessageHandler(Context ctx) {
        int message_id = ctx.attribute("message_id");
        if ((messageService.getMessageById(message_id) != null)) {
            ctx.json(messageService.deleteMessage(message_id));
        } else {
            ctx.status(400);
        }
    }


}