import unittest
import CAO3.prod.StadiumJumpScore as StadiumJumpScore

class StadiumJumpScoreTest(unittest.TestCase):
    
    def scenario01(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        constructed = myJumpScore.isinstance(StadiumJumpScore)
        self.assertEqual(constructed, True)
        
    def scenario02_1(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        self.assertEqual(myJumpScore.isComplete(), False)
        
    def scenario02_3(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        myJumpScore.setComplete()
        self.assertEqual(myJumpScore.isComplete(), True)
    
    def scenario02_5(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        self.assertEqual(myJumpScore.isEliminated(), False)
        
    def scenario02_7(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        myJumpScore.setEliminated()
        self.assertEqual(myJumpScore.isEliminated(), True)
        
    def scenario02_9(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        self.assertEqual(myJumpScore.isWithdrawn(), False)
        
    def scenario02_11(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        myJumpScore.setWithdrawn()
        self.assertEqual(myJumpScore.isWithdrawn(), True)
        
    def scenario02_13(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        self.assertEqual(myJumpScore.isRetired(), False)
        
    def scenario02_15(self):
        myJumpScore = StadiumJumpScore.StadiumJumpScore()
        myJumpScore.setRetired()
        self.assertEqual(myJumpScore.isRetired(), True)
        
        
    
        