package com.jts.rest_api.service;
import com.jts.rest_api.model.Client;

import java.util.*;

public interface ClientService {
    void create(Client client);
    List<Client> readAll();
    Client read(int id);
    boolean update(Client client, int id);
    boolean delete(int id);
}
