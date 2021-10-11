package datastore;


import ad.service.AdService;
import ad.service.CategoryService;
import lombok.SneakyThrows;
import user.entity.Role;
import user.entity.User;
import user.service.UserService;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.time.LocalDate;

@ApplicationScoped
public class InitializationData {

    /**
     * Service for handle Users operations.
     */
    private final UserService userService;

    /**
     * Service for handle Ads operation.
     */
    private final AdService adService;

    /**
     * Service for handle Categories operations.
     */
    private final CategoryService categoryService;

    @Inject
    public InitializationData(UserService userService, AdService adService, CategoryService categoryService)
    {
        this.userService = userService;
        this.adService = adService;
        this.categoryService = categoryService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }

    private synchronized void init(){

        User admin = User.builder()
                .login("admin")
                .name("Administrator")
                .surname("Administratorowy")
                .birthDate(LocalDate.of(1999, 10, 10))
                .password("admin")
                .role(Role.ADMIN)
                .email("asdasd@wp.pl")
                .avatar(getResourceAsByteArray("avatars/admin.jpg")) //package relative path
                .build();

        User user1 = User.builder()
                .login("creativexvc")
                .name("Paweu")
                .surname("Lesnieu")
                .birthDate(LocalDate.of(1998, 5, 17))
                .password("admin")
                .role(Role.ADMIN)
                .email("uyiouyio@wp.pl")
                .avatar(getResourceAsByteArray("avatars/creativexvc.jpg"))//package relative path
                .build();

        User user2 = User.builder()
                .login("student")
                .name("Student")
                .surname("Studentowy")
                .birthDate(LocalDate.of(2000, 2, 26))
                .password("admin")
                .role(Role.USER)
                .email("dfhghdfgh@wp.pl")
                .avatar(getResourceAsByteArray("avatars/student.jpg"))//package relative path
                .build();

        User user3 = User.builder()
                .login("prowadzoncy")
                .name("Java EE")
                .surname("Master")
                .role(Role.USER)
                .birthDate(LocalDate.of(1993, 8, 11))
                .password("admin")
                .email("zcvzxcvzxcv@wp.pl")
                .avatar(getResourceAsByteArray("avatars/prowadzoncy.jpg"))//package relative path
                .build();

        User user4 = User.builder()
                .login("bezzdjec")
                .name("Jacek")
                .surname("Siema")
                .role(Role.USER)
                .birthDate(LocalDate.of(1990, 2, 2))
                .password("admin")
                .email("zcvzxsdp@sd.pl")
                .build();

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(admin);
        userService.create(user4);

//        Category automotive = Category.builder()
//                .name("Motoryzacja")
//                .build();
//
//        Category estate = Category.builder()
//                .name("Nieruchomosci")
//                .build();
//
//        Category electronics = Category.builder()
//                .name("Elektronika")
//                .build();
//
//        categoryService.create(automotive);
//        categoryService.create(estate);
//        categoryService.create(electronics);
//
//        Ad ad1 = Ad.builder()
//                .title("Opel Kadet - niezniszczalna jednostka.")
//                .description("Sprzedam Opla, 65 KM, 22 sekundy do setki. Ekonomiczny silnik, wykonanie jeszcze niemieckie, rok 1986.")
//                .category(automotive)
//                .user(user1)
//                .build();
//
//        Ad ad2 = Ad.builder()
//                .title("Audi A8 D3 - limuzyna")
//                .description("Audi 2005 rok, stan idealny, rozrząd wymieniony 2 miesiące temu, przebieg 600k km.")
//                .category(automotive)
//                .user(user1)
//                .build();
//
//        Ad ad3 = Ad.builder()
//                .title("Mieszkanie w Gdańsku 56m^2")
//                .description("Mieszkanie Gdańsk 56m^2 blisko środmieścia, w Pruszczu gdańskim, cena okazyjna 900 tyś. zł. ")
//                .category(estate)
//                .user(user1)
//                .build();
//
//        Ad ad4 = Ad.builder()
//                .title("iPhone 8 plus, wszystko sprawne")
//                .description("Sprzedam iPhone 8 plus, tylko wysyłka za przedplatą, stan jak na zdjęciach, używany przez kobiete. ")
//                .category(electronics)
//                .user(user1)
//                .build();
//
//        Ad ad5 = Ad.builder()
//                .title("Mercedes G klasa, comfort")
//                .description("Mercedes G klasa, comfort, świetnie sie prowadzi, jak droga jest prosta to ma autopilota.")
//                .category(automotive)
//                .user(user2)
//                .build();
//
//        Ad ad6 = Ad.builder()
//                .title("Działka ROD, 2 ary, świetna okolica, trochę zalana.")
//                .description("Tak jak w opisie, cena wywoławcza 80 tyś. do negocjacji.")
//                .category(estate)
//                .user(user2)
//                .build();
//
//        Ad ad7 = Ad.builder()
//                .title("Mieszkanie w Sopocie 16m^2")
//                .description("Jeden pokój z kibelkiem i gazówką, blat roboczy, idealny dla młodego małżeństwa z dzieckiem. Cena: 350 000 zł. ")
//                .category(estate)
//                .user(user2)
//                .build();
//
//        Ad ad8 = Ad.builder()
//                .title("airPods (nie)oryginalne w cenie oryginalnych")
//                .description("Sprzedam sluchawki airPads firmy manta, wyglądają identycznie jak airPodsy firmy apple, aczkolwiek nie ma w nich głośników.")
//                .category(electronics)
//                .user(user2)
//                .build();
//
//
//        adService.create(ad1);
//        adService.create(ad2);
//        adService.create(ad3);
//        adService.create(ad4);
//        adService.create(ad5);
//        adService.create(ad6);
//        adService.create(ad7);
//        adService.create(ad8);



    }
}
