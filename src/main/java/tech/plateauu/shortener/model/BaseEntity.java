package tech.plateauu.shortener.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tech.plateauu.shortener.utils.LocalDateTimeDeserializer;
import tech.plateauu.shortener.utils.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@MappedSuperclass
class BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @CreationTimestamp
    private LocalDateTime creationDate = LocalDateTime.now();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @UpdateTimestamp
    private LocalDateTime modificationDate = LocalDateTime.now();

    @Version
    private int version;

}
