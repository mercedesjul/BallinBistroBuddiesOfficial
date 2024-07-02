package lulian.ballinbistrobuddies.model;

public class UserModel implements UserModelInterface{

    private String name;
    private String theme;

    public UserModel(String name, String theme) {
        this.name = name;
        this.theme = theme;
    }

    @Override
    public String getName() {return name;}
    @Override
    public void setName(String name) {this.name = name;}

    @Override
    public String getTheme() {return theme;}
    @Override
    public void setTheme(String theme) {this.theme = theme;}
}
