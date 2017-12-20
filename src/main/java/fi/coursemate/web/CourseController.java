package fi.coursemate.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.coursemate.domain.Course;
import fi.coursemate.domain.CourseRepository;
import fi.coursemate.domain.PeerReview;
import fi.coursemate.domain.PeerReviewRepository;
import fi.coursemate.domain.Student;
import fi.coursemate.domain.StudentRepository;

@Controller
public class CourseController {
	@Autowired
    private StudentRepository repository; 

	@Autowired
    private CourseRepository crepository; 		

	@Autowired
    private PeerReviewRepository prepository; 		

	@RequestMapping("/courses")
	public String index(Model model) {
		List<Course> courses = (List<Course>) crepository.findAll();
		System.out.println("Count: " + courses.size());
		model.addAttribute("courses", courses);
    	return "courses";
    }

	@RequestMapping("/coursestudents/{id}")
	public String coursestudents(@PathVariable("id") Long courseid, Model model) {
		Course course = crepository.findOne(courseid);
		List<Student> students = new ArrayList<Student>();
		students.addAll(course.getStudents());
		model.addAttribute("students", students);
		model.addAttribute("courseid", courseid);
		return "coursestudents";
    }	

	@PreAuthorize("hasAuthority('ADMIN')")	
    @RequestMapping(value = "addcourse")
    public String addCourse(Model model){
    	model.addAttribute("course", new Course());
        return "addCourse";
    }	

	@PreAuthorize("hasAuthority('ADMIN')")	
    @RequestMapping(value = "/editcourse/{id}")
    public String editCourse(@PathVariable("id") Long courseId, Model model){
    	model.addAttribute("course", crepository.findOne(courseId));
        return "editCourse";
    }	    
	
	@PreAuthorize("hasAuthority('ADMIN')")    
    @RequestMapping(value = "savecourse", method = RequestMethod.POST)
    public String save(Course course){
        crepository.save(course);
    	return "redirect:/courses";
    }
    
	@PreAuthorize("hasAuthority('ADMIN')")	
    @RequestMapping(value = "/deletecourse/{id}", method = RequestMethod.GET)
    public String deleteCourse(@PathVariable("id") Long courseId, Model model) {
    	crepository.delete(courseId);
        return "redirect:/courses";
    }       

    /**
     * Create peer review
     * 
     * @param studentId
     * @param courseId
     * @param reviewer
     * @param model
     * @return review view
     */
    @RequestMapping(value = "/review/{id}/{courseid}/{reviewer}")
    public String review(@PathVariable("id") Long studentId, @PathVariable("courseid") Long courseId, @PathVariable("reviewer") String reviewer, Model model){
    	Student s = repository.findOne(studentId);
    	Course c = crepository.findOne(courseId);
    	//TODO: query not working
    	// Check if review already exist
    	List<PeerReview> reviews = prepository.findByStudentAndCourseidAndCreatedBy(s, courseId, reviewer);
    	PeerReview review;
    	if (!reviews.isEmpty())
    		review = reviews.get(0);
    	else
    		review = new PeerReview(s, courseId);
    	model.addAttribute("review", review);
        return "review";
    }	

    @RequestMapping(value = "savereview", method = RequestMethod.POST)
    public String save(PeerReview review) {
    	Long courseid = review.getCourseid();
        prepository.save(review);
    	return "redirect:/coursestudents/" + Long.toString(courseid);
    }

	/**
	 * Show all reviews
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/reviews")
	public String reviewList(Model model) {
		List<PeerReview> reviews = (List<PeerReview>) prepository.findAllByOrderByStudentAsc();
		model.addAttribute("reviews", reviews);
    	return "reviews";
    }
    
	/**
	 * Show reviews by course
	 * @param courseId
	 * @param model
	 * @return
	 */
	@RequestMapping("/reviews/{courseid}")
	public String courseReviews(@PathVariable("courseid") Long courseId, Model model) {
		List<PeerReview> reviews = (List<PeerReview>) prepository.findByCourseidOrderByStudentAscCourseidAsc(courseId);
		model.addAttribute("reviews", reviews);
    	return "coursereviews";
    }
    	
}
