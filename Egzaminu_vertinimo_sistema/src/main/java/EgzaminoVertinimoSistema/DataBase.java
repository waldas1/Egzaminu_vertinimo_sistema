package EgzaminoVertinimoSistema;

import EgzaminoVertinimoSistema.Exam.Exception.UserException;
import EgzaminoVertinimoSistema.Users.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private File file = new File("StudentaiIrDestytojai.json");
    private List<User> users = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();
    private SimpleModule module = new SimpleModule();

    public User getUser(String name, String psw) throws IOException, UserException {
        users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(name) && user.getPassword().equals(psw)) {
                return user;
            }
        }
        throw new UserException("Blogas username arba slaptazodis");
    }

    public List<User> getAllUsers() throws IOException {
        checkFile();
        // module.addDeserializer(User.class, new UserDeserializer());
        // mapper.registerModule(module);

        if (file.length() != 0) {
            users = mapper.readValue(file, new TypeReference<>() {
            });
        }
        return users;
    }

    public User addNewUser(User user) throws IOException, UserException {
        users = getAllUsers();

        for (User userCheck : users) {
            if (userCheck.getUsername().equals(user.getUsername())) {
                throw new UserException("Toks vartotojas jau egzistuoja!");
            }
        }
        newRegistration(user);

        return user;
    }

    public void newRegistration(User user) throws IOException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //  module.addSerializer(User.class, new UserSerializer());
        //  mapper.registerModule(module);
        checkFile();
        users.add(user);
        mapper.writeValue(file, users);
    }

    private void checkFile() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
