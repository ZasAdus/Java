import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Polynomial {
    private final ArrayList<Double> polynomial;
    private final int degree;
    private boolean isPolynomial = true;
    public boolean isPolynomial(){
        return isPolynomial;
    }
    private final int MAX_DEGREE = 10000;

    private Polynomial(Double[] x) {
        this.degree = x.length - 1;
        this.polynomial = new ArrayList<>(Arrays.asList(x));
    }

    public Polynomial(String text) {
        polynomial = new ArrayList<>();
        int maxPower = -1;

        try {
            for (int j = 0; j <= MAX_DEGREE; j++) {
                polynomial.add(0.0);
            }
            String[] terms = text.split("\\s+");
            StringBuilder base = new StringBuilder();
            for (String term : terms) {
                if(term.equals("+") || term.equals("-")) {
                    base = new StringBuilder(term);
                    continue;
                }
                int j = 0;
                while (j < term.length()) {
                    if(Character.isDigit(term.charAt(j)) || term.charAt(j) == '.' || term.charAt(j) == '-') {
                        base.append(term.charAt(j));
                        j++;
                        if(j == term.length()){
                            double constant = Double.parseDouble(base.toString());
                            this.polynomial.set(0, this.polynomial.get(0) + constant);
                            base = new StringBuilder();
                            break;
                        }
                    } else if (term.charAt(j) == 'x') {
                        j++;
                        int powerInt = 1;
                        if (j < term.length() && term.charAt(j) == '^') {
                            j++;
                            StringBuilder power = new StringBuilder();
                            while (j < term.length() && (Character.isDigit(term.charAt(j)))) {
                                power.append(term.charAt(j));
                                j++;
                            }
                            if(j != term.length()){
                                isPolynomial = false;
                                break;
                            }
                            powerInt = Integer.parseInt(power.toString());
                        }
                        if(powerInt <= MAX_DEGREE) {
                            maxPower = Math.max(maxPower, powerInt);
                            if(Objects.equals(base.toString(), "-")){
                                base = new StringBuilder("-1");
                            }else if(Objects.equals(base.toString(), "")){
                                base = new StringBuilder("1");
                            }
                            double coefficient = (base.toString().isEmpty()) ? 1.0 : Double.parseDouble(base.toString());
                            polynomial.set(powerInt, polynomial.get(powerInt) + Math.round(coefficient * 100.0) / 100.0);
                        }else {
                            double constant = Double.parseDouble(base.toString());
                            this.polynomial.set(0, this.polynomial.get(0) + constant);
                        }
                        base = new StringBuilder();
                    }else{
                        base = new StringBuilder();
                        isPolynomial = false;
                        break;
                    }
                }
            }
        }catch(NumberFormatException e) {
            isPolynomial = false;
            System.out.println("Error Acquired: " + e);
        } finally {
            if(maxPower == -1){
                degree = 0;
            }else{
                degree = maxPower;
            }
        }
    }

    public Polynomial add(Polynomial polynomial2) {
        int newDegree = Math.max(this.degree, polynomial2.degree);
        ArrayList<Double> newPolynomial = new ArrayList<>(newDegree + 1);

        for (int i = 0; i <= newDegree; i++) {
            double term1 = (i <= this.degree) ? this.polynomial.get(i) : 0;
            double term2 = (i <= polynomial2.degree) ? polynomial2.polynomial.get(i) : 0;
            newPolynomial.add(term1 + term2);
        }

        return new Polynomial(newPolynomial.toArray(new Double[0]));
    }


    public Polynomial subtract(Polynomial polynomial2) {
        int newDegree = Math.max(this.degree, polynomial2.degree);
        ArrayList<Double> newPolynomial = new ArrayList<>(newDegree + 1);

        for (int i = 0; i <= newDegree; i++) {
            double term1 = (i <= this.degree) ? this.polynomial.get(i) : 0;
            double term2 = (i <= polynomial2.degree) ? polynomial2.polynomial.get(i) : 0;

            newPolynomial.add(term1 - term2);
        }

        return new Polynomial(newPolynomial.toArray(new Double[0]));
    }

    public Polynomial multiply(Polynomial polynomial2) {
        if (this.degree == 0 && polynomial2.degree == 0 && !(this.polynomial.get(0) == 0.0 && polynomial2.polynomial.get(0) == 0.0)) {
            Double[] newPolynomial = new Double[]{this.polynomial.get(0) * (polynomial2.polynomial.get(0))};
            return new Polynomial(newPolynomial);
        }else if(this.polynomial.get(0) == 0.0 && polynomial2.polynomial.get(0) == 0.0){
            return new Polynomial("0.0");
        }else if(this.degree == 0 && this.polynomial.get(0) == 0.0){
            return new Polynomial("0.0");
        }else if(polynomial2.degree == 0 && polynomial2.polynomial.get(0) == 0.0) {
            return new Polynomial("0.0");
        }else{
            ArrayList<Double> newPolynomial = this.getDoubles(polynomial2);
            return new Polynomial(newPolynomial.toArray(new Double[0]));
        }
    }
    private ArrayList<Double> getDoubles(Polynomial polynomial) {
        int newDegree = this.degree + polynomial.degree;
        ArrayList<Double> newPolynomial = new ArrayList<>(Collections.nCopies(newDegree + 1, 0.0D));

        for(int i = 0; i <= this.degree; ++i) {
            for(int j = 0; j <= polynomial.degree; ++j) {
                int power = i + j;
                double coefficient = (double)Math.round(this.polynomial.get(i) * polynomial.polynomial.get(j) * 100.0D) / 100.0D;
                newPolynomial.set(power, newPolynomial.get(power) + coefficient);
            }
        }
        return newPolynomial;
    }

    public String toString() {
        StringBuilder base = new StringBuilder();

        if (this.degree == 0) {
            if (this.polynomial.get(0) > 0.0) {
                base.append(this.polynomial.get(0));
            } else if (this.polynomial.get(0) < 0.0) {
                base.append("- ").append(Math.abs(this.polynomial.get(0)));
            } else {
                base.append("0.0");
            }
        } else {
            boolean flag = true;

            if (!this.polynomial.isEmpty()) {
                if (this.polynomial.get(0) < 0.0D) {
                    base.append("- ").append(Math.abs(this.polynomial.get(0))).append(" ");
                    flag = false;
                } else if (this.polynomial.get(0) > 0.0D) {
                    base.append(this.polynomial.get(0)).append(" ");
                    flag = false;
                }

                if (this.polynomial.get(1) < 0.0D) {
                    flag = false;
                    base.append("- ").append(Math.abs(this.polynomial.get(1))).append("x ");
                } else if (this.polynomial.get(1) > 0.0D) {
                    if (!flag) {
                        base.append("+ ").append(this.polynomial.get(1)).append("x ");
                    } else {
                        flag = false;
                        base.append(this.polynomial.get(1)).append("x ");
                    }
                }

                for (int i = 2; i <= this.degree; ++i) {
                    if (this.polynomial.get(i) != 0.0D) {
                        if (this.polynomial.get(i) > 0.0D) {
                            if (!flag) {
                                base.append("+ ").append(this.polynomial.get(i)).append("x^").append(i).append(" ");
                            } else {
                                flag = false;
                                base.append(this.polynomial.get(i)).append("x^").append(i).append(" ");
                            }
                        } else if (this.polynomial.get(i) < 0.0D) {
                            flag = false;
                            base.append("- ").append(Math.abs(this.polynomial.get(i))).append("x^").append(i).append(" ");
                        }
                    }
                }
            }
        }

        return base.toString();
    }
    public double valueForX(double x) {
        if (this.degree == -1) {
            return this.polynomial.get(0);
        } else {
            double result = 0.0;
            if (this.degree > MAX_DEGREE) {
                result = this.polynomial.get(this.degree);
            }
            for(int i = this.degree; i >= 0; --i) {
                result = result * x + this.polynomial.get(i);
            }
            return result;
        }
    }

    public static void main(String[] args) {

    }
}
