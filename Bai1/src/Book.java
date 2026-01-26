import java.util.Scanner;

public class Book {
    private int id;
    private String title;
    private String author;
    private long price;

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }

    public Book(int id, String title, String author, long price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }
    public void input(){
        Scanner x = new Scanner(System.in);
        System.out.println("Nhập mã sách: ");
        this.id = Integer.parseInt(x.nextLine());
        System.out.println("Nhập tên sách: ");
        this.title = x.nextLine();
        System.out.println("Nhập tên tác giả: ");
        this.author = x.nextLine();
        System.out.println("Nhập giá sách: ");
        this.price = x.nextLong();
    }
    public void output(){
        String msg = """
                BOOK: id =%d, title =%s, author =%s, price =%d""".formatted(id, title, author, price);
        System.out.println(msg);
    }
}
