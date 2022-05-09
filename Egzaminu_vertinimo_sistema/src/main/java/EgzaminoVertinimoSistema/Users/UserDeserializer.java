package EgzaminoVertinimoSistema.Users;

import EgzaminoVertinimoSistema.Users.User;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {

    protected UserDeserializer() {
        super(User.class);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        User user = new User();
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        JsonNode node = jsonNode.get("id");
        if (node != null) {
            user.setId(node.asInt());
        }

        node = jsonNode.get("username");
        if (node != null) {
            user.setUsername(node.asText());
        }

        node = jsonNode.get("password");
        if (node != null){
            user.setPassword(node.asText());
        }

        node = jsonNode.get("name");
        if (node != null) {
            user.setName(node.asText());
        }

        node = jsonNode.get("surname");
        if (node != null) {
            user.setSurname(node.asText());
        }

        node = jsonNode.get("role");
        if (node != null) {
            user.setRole(node.asText());
        }
        return user;
    }
}
