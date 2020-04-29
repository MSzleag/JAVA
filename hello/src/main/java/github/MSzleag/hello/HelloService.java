package github.MSzleag.hello;

import github.MSzleag.lang.Lang;
import github.MSzleag.lang.LangRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
class HelloService {
    static final String FALLBACK_NAME = "world";
    static final Lang FALLBACK_LANG = new Lang(1,"Hello", "en");

    private LangRepository repository;


    public HelloService(LangRepository repository) { this.repository = repository; }

    String prepareGreeting(String name, Integer langId){
        langId = Optional.ofNullable(langId).orElse((FALLBACK_LANG.getId()));
        var welcomeMsg = repository.findById(langId).orElse(FALLBACK_LANG).getWelcomeMsg();
        var nameToWelcome =   Optional.ofNullable(name).orElse(FALLBACK_NAME);
        return  welcomeMsg + " " + nameToWelcome + "!";
    }
}
