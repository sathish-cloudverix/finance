package com.bfits.common.util.mongo;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoReportRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Document> getDailyReport() {
        MongoDatabase database = mongoTemplate.getDb();
        return database.runCommand(new Document("$eval", "generateDailyReport()"))
                .getList("retval", Document.class);
    }
}
