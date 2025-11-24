package model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String courseId;
    private final String name;
    private final int credits;

    private final List<String> prerequisiteIds;

    public Course(String courseId, String name, int credits, List<String> prerequisiteIds) {
        this.courseId = Objects.requireNonNull(courseId);
        this.name = Objects.requireNonNull(name);
        this.credits = credits;
        this.prerequisiteIds = prerequisiteIds != null ? Collections.unmodifiableList(prerequisiteIds) : Collections.emptyList();
    }

    public String getCourseId() { return courseId; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public List<String> getPrerequisiteIds() { return prerequisiteIds; }

    @Override
    public String toString() {
        return String.format("%s (%s credits). Prereqs: %s", name, credits, prerequisiteIds.isEmpty() ? "None" : String.join(", ", prerequisiteIds));
    }
}