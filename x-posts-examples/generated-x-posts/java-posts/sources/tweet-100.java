// ❌ Manual style — every PR looks different
public class OrderService{
public void place( Order o ){
if(o.total()>0){ repo.save(o); }
}
}

// ✅ Same rules everywhere (Spotless / formatter)
public class OrderService {
    public void place(Order o) {
        if (o.total() > 0) {
            repo.save(o);
        }
    }
}

// build.gradle — Spotless example
spotless {
    java {
        googleJavaFormat()
    }
}
