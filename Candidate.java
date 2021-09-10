public class Candidate
{
    private String candidate;
    
    Candidate() {
        this.candidate = "123456789";
    }
    
    public void initCandidate() {
        this.candidate = "";
    }
    
    public void setCandidate(final String s) {
        this.candidate = s;
    }
    
    public String getCandidate() {
        return this.candidate;
    }
    
    public void addCandidate(final String s) {
        this.candidate = String.valueOf(this.candidate) + s;
    }
    
    public boolean subCandidate(final String s) {
        final int a = this.candidate.length();
        this.candidate = this.candidate.replaceAll(s, "");
        return a != this.candidate.length();
    }
    
    public String sumCandidate(final String[] candidates, final int power) {
        String SUM = "";
        for (int i = 0; i < power; ++i) {
            for (int j = 0; j < candidates[i].length(); ++j) {
                if (SUM.indexOf(candidates[i].substring(j, j + 1)) == -1) {
                    SUM = String.valueOf(SUM) + candidates[i].substring(j, j + 1);
                }
            }
        }
        return SUM;
    }
}