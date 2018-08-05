import unittest
import CA01.prod.values as values


class ValuesTest(unittest.TestCase):

    def test010ReturnsMedianOddList(self):
        myValues = values.Values([1,2,3])
        self.assertEquals(2,myValues.median())
    
    def test02ReturnsMedianEvenList(self):
        myValues02 = values.Values([1, 3, 2, 2])
        self.assertEquals(2,myValues02.median())
        
    def test03ReturnsProductPositivePositive(self):
        myValues03 = values.Values([2, 4])
        self.assertEquals(8, myValues03.product())
        
    def test04ReturnsProductNegativePositive(self):
        myValues04 = values.Values([-2, 4])
        self.assertEquals(-8, myValues04.product())        
        
    def test05ReturnsProductNegativeNegative(self):
        myValues05 = values.Values([-2, -4])
        self.assertEquals(8, myValues05.product())
        
    def test06ReturnsProductTripleNegative(self):
        myValues06 = values.Values([-2, -2, -2])
        self.assertEquals(-8, myValues06.product())
        
    def test07PenultimateException(self):
        myValues07 = values.Values([1])
        self.assertRaises(ValueError,myValues07.penultimate)
        
    def test08ReturnsPenultimateEvenLength(self):
        myValues08 = values.Values([1, 2, 3, 4])
        self.assertEquals(3, myValues08.penultimate())
        
    def test09ReturnsPenultimateOddLength(self):
        myValues09 = values.Values([1, 2, 3, 4, 5])
        self.assertEquals(4, myValues09.penultimate())
        
    def test10ReturnsStdDevEvenLengthPositive(self):
        myValues10 = values.Values([1, 2, 3, 4])
        self.assertAlmostEqual(1.29, myValues10.stdev(), 2, "")
    
    def test11ReturnsStdDevOddLengthPositive(self):
        myValues11 = values.Values([1, 2, 3, 4, 5])
        self.assertAlmostEqual(1.58, myValues11.stdev(), 2, "")
    
    def test12ReturnsStdDevEvenLengthNegative(self):
        myValues12 = values.Values([-1, 0, 1, 2])
        self.assertAlmostEqual(1.29, myValues12.stdev(), 2, "")
        
    def test13ReturnsStdDevOddLengthNegative(self):
        myValues13 = values.Values([-2, -1, 0, 1, 2])
        self.assertAlmostEquals(1.58, myValues13.stdev(), 2, "")
        
    
        