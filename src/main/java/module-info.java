module src.main.java.lanternagame {
  requires javafx.media;
  requires com.googlecode.lanterna;

  opens lanternagame to javafx.fxml;
  exports lanternagame;
}