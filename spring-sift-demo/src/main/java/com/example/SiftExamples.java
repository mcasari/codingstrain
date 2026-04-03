package com.example;

import java.util.List;
import java.util.Map;

import com.mirkoddd.sift.core.Delimiter;
import com.mirkoddd.sift.core.NamedCapture;
import com.mirkoddd.sift.core.Sift;
import com.mirkoddd.sift.core.SiftCatalog;
import com.mirkoddd.sift.core.SiftPatterns;
import com.mirkoddd.sift.core.dsl.Fragment;
import com.mirkoddd.sift.core.dsl.SiftPattern;
import com.mirkoddd.sift.core.engine.SiftCompiledPattern;

/**
 * Comprehensive examples from Sift documentation
 * A Fluent Regex Builder for Java
 */
public class SiftExamples {

    public static void main(String[] args) {
        System.out.println("=== Sift Examples ===\n");
        
        // ===========================================================
        // 1. BASIC USAGE EXAMPLE: hexColor pattern ^#[0-9a-fA-F]{6}$
        // ==========================================================
        
        //BASIC USAGE EXAMPLE: hexColor pattern ^#[0-9a-fA-F]{6}$
             
        //You can define the pattern in a more expressive way starting from the Sift domain specific language 
		String hexColor = Sift.fromStart()
				.character('#')
				.then()
				.exactly(6)
				.hexDigits() 
				.andNothingElse().shake();
		
		SiftCompiledPattern compiledPattern = Sift.fromStart().character('#').then().exactly(6).hexDigits() // [0-9a-fA-F]{6}
		.andNothingElse().sieve();

        	// ^#[0-9a-fA-F]{6}$
        
        
        // Old way - cryptic regex
        // Pattern p = Pattern.compile("^(?=[\\p{Lu}])[\\p{L}\\p{Nd}_]{3,15}+[0-9]?$");
        
        // Sift way - readable and type-safe
        String regex = Sift.fromStart()
                .exactly(1).upperCaseLettersUnicode()
                .then()
                .between(3, 15).wordCharactersUnicode().withoutBacktracking()
                .then()
                .optional().digits()
                .andNothingElse()
                .shake();
        
        System.out.println("Generated regex: " + regex);
        System.out.println();

        // ============================================
        // 2. ENTRY POINTS
        // ============================================
        System.out.println("2. ENTRY POINTS");
        
        String fromStart = Sift.fromStart().oneOrMore().digits().shake();
        System.out.println("fromStart(): " + fromStart);
                
        String fromAnywhere = Sift.fromAnywhere().oneOrMore().wordCharacters().shake();
        System.out.println("fromAnywhere(): " + fromAnywhere);
                
        String fromPreviousMatchEnd = Sift.fromPreviousMatchEnd().exactly(3).digits().shake();
        System.out.println("fromPreviousMatchEnd(): " + fromPreviousMatchEnd);
       

        // ============================================
        // 3. TERMINAL METHODS
        // ============================================
        System.out.println("3. TERMINAL METHODS");
        
        String rawRegex = Sift.fromStart().oneOrMore().digits().shake();
        System.out.println("shake(): " + rawRegex);
        
        SiftCompiledPattern compiled = Sift.fromStart().oneOrMore().digits().sieve();
        System.out.println("sieve() compiled: " + compiled);
        
        // ============================================
        // 4. MODULAR COMPOSITION
        // ============================================
        System.out.println("4. MODULAR COMPOSITION");
       
        // ============================================
        // 5. DATA EXTRACTION
        // ============================================
        System.out.println("5. DATA EXTRACTION");
        
        NamedCapture yearGroup = SiftPatterns.capture("year", Sift.exactly(4).digits());
        NamedCapture monthGroup = SiftPatterns.capture("month", Sift.exactly(2).digits());
        NamedCapture dayGroup = SiftPatterns.capture("day", Sift.exactly(2).digits());
        
        SiftPattern<?> datePattern = Sift.fromStart()
                .namedCapture(yearGroup)
                .followedBy('-')
                .then().namedCapture(monthGroup)
                .followedBy('-')
                .then().namedCapture(dayGroup)
                .andNothingElse();
        
        Map<String, String> fields = datePattern.extractGroups("2026-03-13");
        System.out.println("Extracted groups: " + fields);
        
        SiftCompiledPattern digitPattern = Sift.fromAnywhere().oneOrMore().digits().sieve();
        List<String> prices = digitPattern.extractAll("Order: 3 items at 25 and 40 euros");
        System.out.println("All digits found: " + prices);
        
        String largeText = "apple banana cherry date elderberry fig grape";
        System.out.print("Words > 5 chars: ");
        Sift.fromAnywhere().oneOrMore().lettersUnicode()
            .streamMatches(largeText)
            .filter(word -> word.length() > 5)
            .forEach(word -> System.out.print(word + " "));
        System.out.println("\n");

        // ============================================
        // 6. EXTRACTION API METHODS
        // ============================================
        System.out.println("6. EXTRACTION API METHODS");
        
        SiftCompiledPattern pattern = Sift.fromAnywhere().oneOrMore().digits().sieve();
        String testInput = "Price: 100 dollars, 200 euros";
        
        System.out.println("containsMatchIn: " + pattern.containsMatchIn(testInput));
        System.out.println("matchesEntire('100'): " + pattern.matchesEntire("100"));
        System.out.println("extractFirst: " + pattern.extractFirst(testInput));
        System.out.println("extractAll: " + pattern.extractAll(testInput));
        System.out.println("replaceFirst: " + pattern.replaceFirst(testInput, "XXX"));
        System.out.println("replaceAll: " + pattern.replaceAll(testInput, "XXX"));
        System.out.println("splitBy: " + pattern.splitBy("100-200-300"));
        System.out.println();

        // ============================================
        // 7. ReDoS MITIGATION
        // ============================================
        System.out.println("7. ReDoS MITIGATION");
        
        String possessive = Sift.fromAnywhere()
                .oneOrMore().wordCharacters().withoutBacktracking()
                .shake();
        System.out.println("Possessive: " + possessive);
        
        SiftPattern<Fragment> atomic = Sift.fromAnywhere()
                .oneOrMore().digits()
                .preventBacktracking();
        System.out.println("Atomic group: " + atomic.shake());
        
        String lazy = Sift.fromAnywhere()
                .oneOrMore().anyCharacter().asFewAsPossible()
                .shake();
        System.out.println("Lazy quantifier: " + lazy);
        System.out.println();

        // ============================================
        // 8. ALTERNATIVE REGEX ENGINES
        // ============================================
        System.out.println("8. ALTERNATIVE REGEX ENGINES");
        
        SiftCompiledPattern jdkPattern = Sift.fromAnywhere()
                .oneOrMore().digits()
                .sieve();
        System.out.println("JDK engine pattern compiled");
        
        System.out.println("RE2J engine: Uncomment when dependency is available");
        System.out.println("GraalVM engine: Uncomment when dependency is available");
        System.out.println();

        // ============================================
        // 9. LOOKAROUNDS
        // ============================================
        System.out.println("9. LOOKAROUNDS");
        
        SiftPattern<Fragment> pdfFile = Sift.fromAnywhere()
                .oneOrMore().wordCharacters()
                .mustBeFollowedBy(SiftPatterns.literal(".pdf"));
        System.out.println("Positive lookahead: " + pdfFile.shake());
        
        SiftPattern<Fragment> absoluteValue = Sift.fromAnywhere()
                .oneOrMore().digits()
                .notFollowedBy(SiftPatterns.literal("%"));
        System.out.println("Negative lookahead: " + absoluteValue.shake());
        
        SiftPattern<Fragment> dollarAmount = Sift.fromAnywhere()
                .oneOrMore().digits()
                .mustBePrecededBy(SiftPatterns.literal("$"));
        System.out.println("Positive lookbehind: " + dollarAmount.shake());
        
        SiftPattern<Fragment> networkPort = Sift.fromAnywhere()
                .of(SiftPatterns.literal("port"))
                .notPrecededBy(SiftPatterns.literal("pass"));
        System.out.println("Negative lookbehind: " + networkPort.shake());
        System.out.println();

        // ============================================
        // 10. SIFT CATALOG
        // ============================================
        System.out.println("10. SIFT CATALOG");
        
        boolean validEmail = SiftCatalog.email().matchesEntire("user@example.com");
        System.out.println("Email valid: " + validEmail);
        
        String compositeRegex = Sift.fromStart()
                .of(SiftCatalog.uuid())
                .followedBy('/')
                .then().of(SiftCatalog.isoDate())
                .andNothingElse()
                .shake();
        System.out.println("Composite (UUID/ISO Date): " + compositeRegex);
        System.out.println();

        // ============================================
        // 11. RECURSIVE STRUCTURES
        // ============================================
        System.out.println("11. RECURSIVE STRUCTURES");
        
        SiftPattern<Fragment> nested = SiftPatterns.nesting(5)
                .using(Delimiter.PARENTHESES)
                .containing(Sift.fromAnywhere().oneOrMore().lettersUnicode());
        
        boolean hasNested = nested.containsMatchIn("((hello)(world))");
        System.out.println("Nested parens match: " + hasNested);
        System.out.println();

        // ============================================
        // 12. CONDITIONAL PATTERNS
        // ============================================
        System.out.println("12. CONDITIONAL PATTERNS");
        
        SiftPattern<Fragment> price = SiftPatterns
                .ifPrecededBy(SiftPatterns.literal("USD"))
                .thenUse(Sift.fromAnywhere().oneOrMore().digits())
                .otherwiseUse(
                        Sift.fromAnywhere().oneOrMore().digits()
                                .followedBy(Sift.fromAnywhere().character('€'))
                );
        System.out.println("Conditional price pattern created");
        
        SiftPattern<Fragment> format = SiftPatterns
                .ifFollowedBy(SiftPatterns.literal("px"))
                .thenUse(Sift.fromAnywhere().oneOrMore().digits())
                .otherwiseIfFollowedBy(SiftPatterns.literal("%"))
                .thenUse(Sift.fromAnywhere().between(1, 3).digits())
                .otherwiseNothing();
        System.out.println("Else-if conditional pattern created");
        System.out.println();

        // ============================================
        // 13. PATTERN EXPLANATION
        // ============================================
        System.out.println("13. PATTERN EXPLANATION");
        
        SiftPattern<?> explainPattern = Sift.fromStart()
                .oneOrMore().digits()
                .andNothingElse();
        
        // ============================================
        // 14. QUANTIFIERS
        // ============================================
        System.out.println("14. QUANTIFIERS");
        
        System.out.println("zeroOrMore(): " + Sift.fromAnywhere().zeroOrMore().digits().shake());
        System.out.println("oneOrMore(): " + Sift.fromAnywhere().oneOrMore().letters().shake());
        System.out.println("optional(): " + Sift.fromAnywhere().optional().whitespace().shake());
        System.out.println("exactly(3): " + Sift.fromAnywhere().exactly(3).digits().shake());
        System.out.println("atLeast(2): " + Sift.fromAnywhere().atLeast(2).wordCharacters().shake());
        System.out.println("atMost(5): " + Sift.fromAnywhere().atMost(5).anyCharacter().shake());
        System.out.println("between(2, 4): " + Sift.fromAnywhere().between(2, 4).digits().shake());
        System.out.println();

        // ============================================
        // 15. CHARACTER CLASSES
        // ============================================
        System.out.println("15. CHARACTER CLASSES");
        
        System.out.println("digits(): " + Sift.fromAnywhere().digits().shake());
        System.out.println("letters(): " + Sift.fromAnywhere().letters().shake());
        System.out.println("lettersUnicode(): " + Sift.fromAnywhere().lettersUnicode().shake());
        System.out.println("upperCaseLetters(): " + Sift.fromAnywhere().upperCaseLetters().shake());
        System.out.println("upperCaseLettersUnicode(): " + Sift.fromAnywhere().upperCaseLettersUnicode().shake());
        System.out.println("lowerCaseLetters(): " + Sift.fromAnywhere().lowerCaseLetters().shake());
        System.out.println("wordCharacters(): " + Sift.fromAnywhere().wordCharacters().shake());
        System.out.println("wordCharactersUnicode(): " + Sift.fromAnywhere().wordCharactersUnicode().shake());
        System.out.println("whitespace(): " + Sift.fromAnywhere().whitespace().shake());
        System.out.println("anyCharacter(): " + Sift.fromAnywhere().anyCharacter().shake());
        System.out.println("character('a'): " + Sift.fromAnywhere().character('a').shake());
        System.out.println();

        System.out.println("=== All examples completed ===");
    }
}

