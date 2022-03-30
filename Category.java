public enum Category {
    RED("\033[0;31m"), WHITE("\033[0;37m"), BLUE("\033[0;34m"), PURPLE("\033[0;35m"),
    YELLOW("\033[0;33m"), GREEN("\033[0;32m");

    private String colour;

    Category(String our_colour){
        this.colour = our_colour;
    }

    public String getColour() {
        return colour;
    }
}
