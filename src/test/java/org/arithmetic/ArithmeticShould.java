package org.arithmetic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticShould {

    // Rules
    // Throw an error when there are no brackets at the beginning and at the end.
    // Throw an error when there is no even number of parentheses
    // resolve not given operation as zero
    // Calculate the operation according to the PEMDAS rules.

    // Examples
    // "( )" -> "0"
    // "( ( ) )" -> "0"
    // "( 0 + 1 )" -> "1"
    // "( 0 - 1 )" -> "-1"
    // "( 2 * 1 )" -> "2"
    // "( 6 / 2 )" -> "3"
    // "( 1 + ( 0 + 1 ) )" -> "2"
    // "( ( 2 + 3 ) + ( 3 + 2 ) )" -> "10"
    // "( 1 + ( ( 2 + 3 ) * (4 * 5) ) )" -> "101"
    // "3 + ( 2 * 1 )" -> "Invalid record error"
    // "( 3 + ( 2 * 1 )" -> "Invalid record error"
    private Arithmetic arithmetic = new Arithmetic();

    @Test
    void resolve_not_given_operation_as_zero(){
        assertEquals("0", arithmetic.calculate("( )"));
        assertEquals("0", arithmetic.calculate("( ( ) )"));
    }

    @Test
    void calculate_the_most_simple_sum_operation(){
        assertEquals("1", arithmetic.calculate("( 0 + 1 )"));
    }

    @Test
    void calculate_the_most_simple_subtract_operation(){
        assertEquals("-1", arithmetic.calculate("( 0 - 1 )"));
    }

    @Test
    void calculate_the_most_simple_multiply_operation(){
        assertEquals("2", arithmetic.calculate("( 2 * 1 )"));
    }

    @Test
    void calculate_the_most_simple_divide_operation(){
        assertEquals("3", arithmetic.calculate("( 6 / 2 )"));
    }

    @Test
    void calculate_the_most_simple_operation_with_brackets_inside(){
        assertEquals("2", arithmetic.calculate("( 1 + ( 0 + 1 ) )"));
    }

    @Test
    void calculate_the_another_operation_with_brackets_inside(){
        assertEquals("10", arithmetic.calculate("( ( 2 + 3 ) + ( 3 + 2 ) )"));
    }

    @Test
    void calculate_the_another_more_complex_operation_with_brackets_inside(){
        assertEquals("101", arithmetic.calculate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
    }

    @Test
    void calculate_the_another_more_complex_operation_with_some_brackets_inside(){
        assertEquals("-165", arithmetic.calculate("( 5 * ( 4 * ( 3 * ( 2 * ( 1 * 9 ) / 8 - 7 ) + 6 ) ) )"));
    }

    @Test
    void throw_error_when_there_are_not_brackets_at_beginning_and_end(){
        assertEquals("Invalid record error", arithmetic.calculate(" 3 + ( 2 * 1 )"));
    }

    @Test
    void throw_error_when_there_is_no_even_number_of_brackets(){
        assertEquals("Invalid record error", arithmetic.calculate("( 3 + ( 2 * 1 )"));
    }

}

