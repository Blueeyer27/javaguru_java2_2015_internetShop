package lv.javaguru.java2;

/**
 * Created by Anton on 2015.03.08..
 */
public enum RequestType {

//    controllerMapping.put("/index", getBean(IndexController.class));
//    controllerMapping.put("/users", getBean(UsersController.class));
//    controllerMapping.put("/register", getBean(RegisterController.class));
//    controllerMapping.put("/login", getBean(LoginController.class));
//    controllerMapping.put("/logout", getBean(LoginController.class));
//    controllerMapping.put("/about", getBean(AboutCompanyController.class));
//    controllerMapping.put("/cart", getBean(CartController.class));
//    controllerMapping.put("/product", getBean(ProductController.class));
//    controllerMapping.put("/news", getBean(NewItemController.class));
//    controllerMapping.put("/user", getBean(UserInfoController.class));
//    controllerMapping.put("/add_product", getBean(AddProductController.class));

    INDEX("/index"),
    REGISTER("/register"),
    LOGIN("/login"),
    LOGOUT("/logout"),
    ABOUT("/about"),
    CART("/cart"),
    PRODUCT("/product"),
    NEWS("/news"),
    USER("/user"),
    ADD_PRODUCT("/add_product");

    private String typeValue;

    private RequestType(String type) {
        typeValue = type;
    }

    static public RequestType getType(String pType) {
        for (RequestType type: RequestType.values()) {
            if (type.getTypeValue().equals(pType)) {
                return type;
            }
        }
        throw null;
    }

    public String getTypeValue() {
        return typeValue;
    }

}
