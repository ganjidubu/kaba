package home.dubu.kaba.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "search", indexes = @Index(name = "idx__keyword", columnList = "keyword"))
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String keyword;
    private int count;


    public void countUp() {
        this.count += 1;
    }
}
