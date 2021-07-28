package comsy.was.data.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // 빌더 패턴으로만 new 하기 위함.
@AllArgsConstructor(access = AccessLevel.PROTECTED)     // 빌더 패턴으로만 new 하기 위함.
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
