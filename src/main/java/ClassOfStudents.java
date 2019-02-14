import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import students.Student;
import students.Teacher;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A base class that calls the Jackson code to construct itself and in the process several students.
 */
public class ClassOfStudents {

	/**
	 * A list of all of the teachers, including the head teacher.
	 */
	@NotNull
	private final List<Teacher> teachers;

	/**
	 * The head teacher. Should also be in teachers.
	 */
	@NotNull
	private final Teacher head;

	/**
	 * A list of all the students, including the valedictorian.
	 */
	@NotNull
	private final List<Student> students;

	/**
	 * The best student in the class. Should also be in students.
	 */
	@NotNull
	private final Student valedictorian;

	/**
	 * The name of the class.
	 */
	@NotNull
	private final String classname;

	/**
	 * Default constructor
	 * @param teachers A list of all the teachers, including the department head.
	 * @param head The department head. Should also be in teachers.
	 * @param students      A list of all the students, including the valedictorian.
	 * @param valedictorian The best student in the class. Should also be in students.
	 * @param classname     The name of the class.
	 */
	@JsonCreator
	public ClassOfStudents(@NotNull @JsonProperty(required = true) List<Teacher> teachers,
						   @NotNull @JsonProperty(required = true) Teacher head,
						   @NotNull @JsonProperty(required = true) List<Student> students,
						   @NotNull @JsonProperty(required = true) Student valedictorian,
						   @NotNull @JsonProperty(required = true) String classname) {
		this.teachers = teachers;
		this.head = head;
		this.valedictorian = valedictorian;
		this.students = students;
		this.classname = classname;
	}

	/**
	 * Reads the Yaml file and uses it to construct an {@link ClassOfStudents}.
	 *
	 * @param args Command line arguments, not used.
	 * @throws IOException If ClassOfStudents.yml doesn't exist or isn't a proper YAML file.
	 */
	public static void main(String[] args) throws IOException {
		Yaml yaml = new Yaml();
		Map<?, ?> normalized = (Map<?, ?>) yaml.load(new FileReader("example_class.yml"));
		YAMLMapper mapper = new YAMLMapper();
		//Turn the Map read by SnakeYaml into a String so Jackson can read it.
		String fixed = mapper.writeValueAsString(normalized);
		//Use a parameter name module so we don't have to specify name for every field.
		mapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
		//Deserialize the map into an object.
		ClassOfStudents output = mapper.readValue(fixed, ClassOfStudents.class);
		System.out.println("Class name: " + output.classname);
		System.out.println("Teachers: " + output.teachers);
		System.out.println("Department Head: " + output.head);
		System.out.println("Students: " + output.students);
		System.out.println("Valedictorian: " + output.valedictorian);
		System.out.println("Valedictorian is first student in Students: " + (output.valedictorian == output.students.get(0)));
	}
}