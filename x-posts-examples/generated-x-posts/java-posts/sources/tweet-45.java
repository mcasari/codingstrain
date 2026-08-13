// ❌ Shallow — dependency is still the same instance
Main shallow = (Main) super.clone();
// shallow.dependency == this.dependency  → true

// ✅ Deep — clone the nested object too
Main deep = (Main) super.clone();
deep.setDependency((Dependency) this.dependency.clone());
// deep.dependency == this.dependency  → false
