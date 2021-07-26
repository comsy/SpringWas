package comsy.was.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    @Column(name = "guid")
    private Long guid;

    @Column(name = "pm_level", nullable = false)
    private Short pmLevel;

    @Column(name = "pm_name", length = 45)
    private String pmName;
}
