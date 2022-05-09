package EgzaminoVertinimoSistema.Users;

import EgzaminoVertinimoSistema.Users.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        super(User.class);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeStringField("password", user.getPassword());
        jsonGenerator.writeStringField("name", user.getName());
        jsonGenerator.writeStringField("surname", user.getSurname());
        jsonGenerator.writeStringField("role", user.getRole());


        jsonGenerator.writeEndObject();
    }
}
