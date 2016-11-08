package com.example.infinitbank.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kylee on 31/10/2016.
 */

public class NotificationMessage {
    private Actor actor;
    private Action action;
    private Object object;
    private long ts;
    private boolean read = false;

    public NotificationMessage(JSONObject obj) {
        Gson gson = new Gson();
        try {
            actor = gson.fromJson(obj.getJSONObject("actor").toString(), Actor.class);
            action = gson.fromJson(obj.getJSONObject("action").toString(), Action.class);
            object = gson.fromJson(obj.getJSONObject("object").toString(), Object.class);
            if (obj.getInt("read") == 1)
                read = true;
            else
                read = false;
            ts = obj.getLong("ts");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public class Actor {
        private String uid;
        private String role;
        private String name;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Action {
        private String type;
        private String content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class Object {
        private String id;
        private String type;
        private String content;
        private String owner;

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
