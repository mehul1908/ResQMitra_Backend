package com.resqmitra.module.incident.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class VolunteerIdSerializer extends JsonSerializer<Incident> {
    @Override
    public void serialize(Incident inc, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (inc != null) {
            gen.writeString(inc.getIncidentId().toString()); // only serialize the email
        } else {
            gen.writeNull();
        }
    }
}

