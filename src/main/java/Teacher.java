import com.fasterxml.jackson.annotation.*;
import org.jetbrains.annotations.NotNull;

/**
 * A teacher.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class Teacher {

    /**
     * This teacher's name.
     */
    @NotNull
    private final String name;

    /**
     * This teacher's yearly salary.
     */
    private final int salary;

    /**
     * Default constructor.
     *
     * @param name   This teacher's name.
     * @param salary This teacher's yearly salary.
     */
    @JsonCreator
    public Teacher(@NotNull @JsonProperty(required = true) String name,
                   @JsonProperty(required = true) int salary) {
        this.name = name;
        this.salary = salary;
    }

    /**
     * @return This teacher's yearly salary.
     */
    public int getSalary() {
        return salary;
    }

    /**
     * @return This teacher's name.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * @return A string representation of this teacher's name and salary.
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}