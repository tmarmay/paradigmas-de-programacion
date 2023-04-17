import Test.HUnit
import Dibujo (Dibujo(..), figura, juntar, apilar, rot45, rotar, encimar, espejar,
                r180, r270,
                cuarteto, encimar4, ciclar,
                foldDib, mapDib,
                figuras)

import Pred (Pred, cambiar, anyDib, allDib, orP, andP)

-- Define your test cases
testCambiar :: Test
testCambiar = TestCase $ do
    let d = cambiar (\x -> x == 'a') (\f -> (rotar $ figura f)) (encimar (encimar (rotar $ figura 'b') (espejar $ figura 'a')) (figura 'a'))
    assertEqual "figura Failure"
        (encimar (encimar (rotar $ figura 'b') (espejar $ rotar $ figura 'a')) (rotar $ figura 'a')) d

testAnyDibTrue :: Test
testAnyDibTrue = TestCase $ do
    let d = anyDib (\x -> x == 'a') (encimar (encimar (rotar $ figura 'b') (espejar $ figura 'a')) (figura 'a'))
    assertEqual "anyDib Failure" True d

testAnyDibFalse :: Test
testAnyDibFalse = TestCase $ do
    let d = anyDib (\x -> x == 'a') (encimar (encimar (rotar $ figura 'b') (espejar $ figura 'b')) (figura 'b'))
    assertEqual "anyDib Failure" False d

testAllDibTrue :: Test
testAllDibTrue = TestCase $ do
    let d = allDib (\x -> x == 'a') (encimar (encimar (rotar $ figura 'a') (espejar $ figura 'a')) (figura 'a'))
    assertEqual "allDib Failure" True d

testAllDibFalse :: Test
testAllDibFalse = TestCase $ do
    let d = allDib (\x -> x == 'a') (encimar (encimar (rotar $ figura 'b') (espejar $ figura 'a')) (figura 'a'))
    assertEqual "allDib Failure" False d

testAndPTrue :: Test
testAndPTrue = TestCase $ do
    let d = andP (\x -> x > 5) (\y -> y < 10) 6
    assertEqual "andP Failure" True d

testAndPFalse :: Test
testAndPFalse = TestCase $ do
    let d = andP (\x -> x > 5) (\y -> y < 10) 4
    assertEqual "andP Failure" False d

testOrPTrue :: Test
testOrPTrue = TestCase $ do
    let d = orP (\x -> x > 10) (\y -> y < 5) 4
    assertEqual "orP Failure" True d

testOrPFalse :: Test
testOrPFalse = TestCase $ do
    let d = orP (\x -> x > 10) (\y -> y < 5) 7
    assertEqual "orP Failure" False d

-- Create a test suite and run the tests
tests :: Test
tests = TestList [ testCambiar
                 , testAnyDibTrue
                 , testAnyDibFalse
                 , testAllDibTrue
                 , testAllDibFalse
                 , testAndPTrue
                 , testAndPFalse
                 , testOrPTrue
                 , testOrPFalse
                 ]

main :: IO Counts
main = runTestTT tests