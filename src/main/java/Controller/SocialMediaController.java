package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.get("/accounts/{id}/messages", this::getAllMessagesForAccount);
        app.get("/messages", this::getAllMessages);
        app.post("/messages", this::createMessage);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void register(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account registered = accountService.addAccount(account);
        if(registered != null) {
            context.json(mapper.writeValueAsString(registered));
        } else {
            context.status(400);
        }
    }

    private void login(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account found = accountService.findAccount(account.getUsername());
        if (found != null && found.getPassword().equals(account.getPassword())) {
            context.json(mapper.writeValueAsString(found));
        } else {
            context.status(401);
        }
    }

    private void createMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Account account = accountService.findAccount(message.getPosted_by());
        Message created = account != null ? messageService.createMessage(message) : null;
        if (created != null) {
            context.json(mapper.writeValueAsString(created));
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesForAccount(Context context) {
        int accountId = Integer.parseInt(context.pathParam("id"));
        List<Message> messages = messageService.getAllMessagesForAccount(accountId);
        context.json(messages);
    }

    private void getAllMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageById(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            context.json(mapper.writeValueAsString(message));
        }
    }

    private void deleteMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessage(messageId);
        if (message != null) {
            context.json(message);
        }
    }

    private void updateMessage(Context context) throws JsonProcessingException {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message updated = messageService.updateMessage(messageId, message);
        if (updated != null) {
            context.json(mapper.writeValueAsString(updated));
        } else {
            context.status(400);
        }
    }
}