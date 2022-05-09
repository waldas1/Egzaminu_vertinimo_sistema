package EgzaminoVertinimoSistema.Exam;

import EgzaminoVertinimoSistema.Enum.ExamType;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.List;

public class ExamDeserializer extends StdDeserializer {
    protected ExamDeserializer() {
        super(Exam.class);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Exam exam = new Exam();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        JsonNode node = jsonNode.get("id");
        if (node != null) {
            exam.setId(node.asInt());
        }

        node = jsonNode.get("title");
        if (node != null) {
            exam.setTitle(node.asText());
        }

        node = jsonNode.get("tipas");
        if (node != null) {
            exam.setType(ExamType.valueOf(node.asText()));
        }

        if (node != null) {
            ArrayNode arrayNode = (ArrayNode) node.get("exam");
            List<Qeustions> qeustions = mapper.readValue(arrayNode.toString(), new TypeReference<>() {
            });
            exam.setExams(qeustions);
        }

        return exam;
    }
}
