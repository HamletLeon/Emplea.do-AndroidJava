package hamletleon.empleado_androidjava.infrastructure.dtos;

import hamletleon.empleado_androidjava.infrastructure.enums.JobCategory;

import static hamletleon.empleado_androidjava.infrastructure.enums.JobCategory.NONE;

/**
 * Created by hsantana on 11/11/2017.
 */

public class JobCriteria extends Criteria {
    public int page = 1;
    public int PageSize = 10;
    public @JobCategory
    String JobCategory = NONE;
}
