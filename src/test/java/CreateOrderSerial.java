public class CreateOrderSerial {
    public static final String BLACK = "BLACK";
    public static final String GREY = "GREY";
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public CreateOrderSerial(String[] color) {
        this.firstName = "test";
        this.lastName = "test";
        this.address = "test";
        this.metroStation = "4";
        this.phone = "+7 800 355 35 35";
        this.rentTime = 5;
        this.deliveryDate = "2020-06-06";
        this.comment = "test";
        this.color = color;
    }
    public CreateOrderSerial() {
    }
}

