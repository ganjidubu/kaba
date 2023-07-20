package home.dubu.kaba.client;

import home.dubu.kaba.domain.Places;

public interface ExternalClient {

    Places search(String keyword);
}
