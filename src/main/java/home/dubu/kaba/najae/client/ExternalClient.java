package home.dubu.kaba.najae.client;

import home.dubu.kaba.najae.domain.Places;

public interface ExternalClient {

    Places search(String keyword);
}
