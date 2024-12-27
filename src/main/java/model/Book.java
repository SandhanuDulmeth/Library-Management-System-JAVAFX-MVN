package model;

import lombok.*;

//@Getter
//@Setter

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    private String id;
    private String title;
    private String author;
    private Integer publishedYear;
    private String genre;
    private Double price;
}