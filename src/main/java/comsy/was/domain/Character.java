package comsy.was.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_character")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicUpdate // 변경한 필드만 대응
public class Character {

    @Id
    @GeneratedValue
    @Column(name = "dbKey")
    private Long id;

    @Column(name = "guid")
    private Long guid;

    @Column(name = "category")
    private int category;

    @Column(name = "id")
    private Long characterId;
}
