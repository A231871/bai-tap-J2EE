import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);
        String msg = """
                Chương trình quản lý sách
                    1. Thêm 1 cuốn sách
                    2. Xóa 1 cuốn sách
                    3. Thay đổi sách
                    4. Xuất thông tin
                    5. Tình sách lập trình
                    6. Lấy sách tối đa theo giá
                    7. Tìm kiếm theo tác giả
                    0. Thoát
                    Chọn chức năng: """;
        int chon = 0;
        do{
            System.out.printf(msg);
            chon = x.nextInt();
            x.nextLine();
            switch(chon)
            {
                case 1 -> {
                    Book newBook = new Book();
                    newBook.input();
                    listBook.add(newBook);
                }
                case 2 -> {
                    System.out.println("Nhập vào mã sách cần xóa: ");
                    int bookId = x.nextInt();
                    //Check mã sách
                    Book find = listBook.stream().filter(p -> p.getId() == bookId).findFirst().orElseThrow();
                    listBook.remove(find);
                    System.out.println("Đã xóa sách thành công");
                }
                case 3 -> {
                    System.out.println("Nhập vào mã sách cần điều chỉnh: ");
                    int bookId = x.nextInt();
                    Book find = listBook.stream().filter(p -> p.getId() == bookId).findFirst().orElseThrow();
                }
                case 4 -> {
                    System.out.println("\n Xuất thông tin danh sách");
                    listBook.forEach(p -> p.output());
                }
                case 5 -> {
                    List<Book> listSearch = listBook.stream()
                            .filter(u -> u.getTitle().toLowerCase().contains("lập trình"))
                            .toList();
                    listSearch.forEach(Book::output);
                }
                case 6 -> {
                    System.out.print("Nhập số lượng K: ");
                    int k = x.nextInt();
                    System.out.print("Nhập giá P: ");
                    long p = x.nextLong();
                    x.nextLine();
                    System.out.println("--- Kết quả (Filter + Limit) ---");
                    listBook.stream()
                            .filter(b -> b.getPrice() <= p)
                            .limit(k)
                            .forEach(Book::output);
                }

                case 7 -> {
                    System.out.print("Nhập các tác giả (ngăn cách bởi dấu phẩy): ");
                    String input = x.nextLine();
                    Set<String> authorSet = Arrays.stream(input.split(","))
                            .map(String::trim) // remove spaces around names
                            .collect(Collectors.toSet());
                    System.out.println("Đang tìm sách của: " + authorSet);
                    listBook.stream()
                            .filter(b -> authorSet.contains(b.getAuthor()))
                            .forEach(Book::output);
                }
            }
        }while (chon != 0);
    }
}